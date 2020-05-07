package com.roimolam.project.dal

import com.roimolam.project.constants.WEBSITE_TEXT_DIRECTORY
import com.roimolam.project.data.TextWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Repository
import java.io.File

@Repository
class TextDAL (@Autowired val env: Environment,
               val websiteTextDirectory: String? = env.getProperty(WEBSITE_TEXT_DIRECTORY)) {

    fun writeToTextFile(filePath: String, fileContent: String): TextWrapper {
        val fullPath = "$websiteTextDirectory$filePath.txt"

        File(fullPath).bufferedWriter().use { out -> out.write(fileContent) }

        return readTextFromFile(filePath)
    }

    fun readTextFromFile(filePath: String): TextWrapper {
        val fullPath = "$websiteTextDirectory$filePath.txt"

        var entireText = ""
        File(fullPath).bufferedReader().use { line ->
            var currentLine = line.readLine()
            entireText += currentLine
            while (currentLine != null) {
                currentLine = line.readLine()
                if (currentLine != null) {
                    entireText += "\n$currentLine"
                }
            }
        }

        return TextWrapper(entireText)
    }
}