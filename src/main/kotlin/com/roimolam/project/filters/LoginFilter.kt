package com.roimolam.project.filters

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import javax.servlet.*

class LoginFilter : Filter {

    override fun destroy() {
        // TODO Auto-generated method stub
    }

    // ------------------------------ The method that actually filters-------------------------------------

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

        val req = request as HttpServletRequest
        val res = response as HttpServletResponse

        if (req.method.equals("Options", ignoreCase = true)) {
            chain.doFilter(request, response)
        }

        val path = request.pathInfo

        if (!path.contains(ADMIN_RESOURCE_NAME)) {
            chain.doFilter(request, response)
            return
        }

        if (req.getSession(false) != null) {
            chain.doFilter(request, response)
            return
        }

        res.status = 401
        res.setHeader("ErrorCause", "Couldn't find a login session")
    }

    // ---------------------------------------------------------------------------------------------------

    @Throws(ServletException::class)
    override fun init(arg0: FilterConfig) {
        // TODO Auto-generated method stub

    }

    companion object {
        private const val ADMIN_RESOURCE_NAME = "/admin"
    }

}