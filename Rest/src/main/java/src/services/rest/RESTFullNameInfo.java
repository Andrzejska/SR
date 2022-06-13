package src.services.rest;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Path("/fullname-info")
public class RESTFullNameInfo {
    ApiService apiService = new ApiService();

    @Context
    ServletContext servletContext;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public FileInputStream showInputHTML() throws FileNotFoundException {
        String base = servletContext.getRealPath("/WEB-INF");
        File f = new File(String.format("%s/%s", base, "index.html"));
        return new FileInputStream(f);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String showResultHTML(@FormParam("fullName") String fullName) throws InterruptedException, ExecutionException, IOException {
        try {
            if (fullName.isEmpty()) throw new Error("The argument was not provided.");

            HashMap<String, Object> ethnicityInfo = (HashMap<String, Object>) this.apiService.getEthnicityInfoByFullName(fullName);
            HashMap<String, String> fortune = (HashMap<String, String>) this.apiService.getFortuneForToday();

            double genderProbabilityInPercents = (double) ethnicityInfo.get("gender probability") * 100;
            return "<html> "
                    + "<body>"
                    + "<h3>" + "Full Name: " + ethnicityInfo.get("fullname") + "</h3>" + "</br>"
                    + "<h3>" + "Gender: " + ethnicityInfo.get("gender") + "</h3>" + "</br>"
                    + "<h3>" + "Gender probability: " + genderProbabilityInPercents + "%</h3>" + "</br>"
                    + "<h3>" + "Ethnicity: " + ethnicityInfo.get("ethnicity") + "</h3>" + "</br>"
                    + "<h3>" + "Phrase of the day: " + fortune.get("fortune") + "</h3>" + "</br>"
                    + "</body>"
                    + "</html> ";
        } catch (Error error) {
            return "<html> "
                    + "<title>" + "Error occurred while receiving data" + "</title>"
                    + "<body><h1>" + error.getMessage() + "</body></h1>" +
                    "</html> ";
        }


    }

}
