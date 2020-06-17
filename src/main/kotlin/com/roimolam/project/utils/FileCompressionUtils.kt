package com.roimolam.project.utils

import org.slf4j.Logger
import java.io.File
import java.io.FileOutputStream
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam

fun compressBytes(bytes: ByteArray, logger: Logger, compressionFactor: Float, uncompressedFilePath: String, compressedFilePath: String): ByteArray {
    logger.debug("Initial file size : ${bytes.size}")
    FileOutputStream(uncompressedFilePath).use { stream -> stream.write(bytes) }
    val input = File(uncompressedFilePath)
    val image = ImageIO.read(input)
    val output = File(compressedFilePath)
    val out = FileOutputStream(output)
    val writer = ImageIO.getImageWritersByFormatName("jpg").next()
    val ios = ImageIO.createImageOutputStream(out)
    writer.output = ios
    val param = writer.defaultWriteParam
    if (param.canWriteCompressed()) {
        param.compressionMode = ImageWriteParam.MODE_EXPLICIT
        param.compressionQuality = compressionFactor
        logger.debug("Compression quality : ${param.compressionQuality}")
    }
    writer.write(null, IIOImage(image, null, null), param)
    out.close()
    ios.close()
    writer.dispose()
    input.delete()
    val compressedBase64Bytes= output.readBytes()
    output.delete()
    logger.debug("Compressed file size : ${compressedBase64Bytes.size}")
    return compressedBase64Bytes
}