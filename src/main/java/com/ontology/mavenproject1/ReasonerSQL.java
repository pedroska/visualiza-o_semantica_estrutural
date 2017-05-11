package com.ontology.mavenproject1;

import com.reasoner.ReasonerOntologia;
import java.sql.SQLException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Pedro Ivo
 */
@Path("reasoner")
public class ReasonerSQL {
    
    private ReasonerOntologia ro;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String reasonerPellet() throws SQLException{
       
        String output;
        ro = new ReasonerOntologia();
        output = ro.loadReasoner();
        
        return output;
    }
}
