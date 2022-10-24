package auctionbe.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint , Serializable {
    /*
     * This class will extend Spring's AuthenticationEntryPoint class and override its method commence.
     * It rejects every unauthenticated request and send error code 401
     * */

    /*
     * Serializable in Java allows us to convert an Object to stream that we can send over the network or save it as file or store in DB for later usage
     * */

    /* the java class should have serialVersionUID defined for the class.
     Let’s write a test class just for deserialization of the already serialized file from previous test class.*/
    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
