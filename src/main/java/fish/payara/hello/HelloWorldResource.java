package fish.payara.hello;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.util.Objects;

@Path("/hello")
public class HelloWorldResource {
    @GET
    public Response hello (@QueryParam("name") String name) {
        if ((name == null) || name.trim().isEmpty()) {
            name = "world";
        }
        return Response.ok(name).build();
    }

    @GET
    @Path("/test")
    public Response breaking (@QueryParam("break") String triggerBreak) {
        if (Objects.equals(triggerBreak, "true")) return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Intentional break.").build();

        try {
            String jsonString = "{\"name\":\"John\", \"age\":20, \"address\":{\"street\":\"Wall Street\", \"city\":\"New York\"}}";
            XmlMapper xmlMapper = new XmlMapper();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode json = objectMapper.readTree(jsonString);
            String out = xmlMapper.writeValueAsString(json.toString());

            Class.forName("org.codehaus.stax2.ri.Stax2WriterAdapter");

            return Response.ok("No error").build();
        }
        catch (NoClassDefFoundError expected) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "NoClassDefFoundError: " + expected.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Incorrect error occurred: " + e.getMessage()).build();
        }
    }
}