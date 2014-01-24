
 import org.bson.types.ObjectId;  
 import org.mule.api.MuleEventContext;  
 import org.mule.api.lifecycle.Callable;  
 import com.mongodb.DBObject;  
 import com.mongodb.WriteConcern;  
 import com.mongodb.util.JSON;  
 
 public class InsertMongo  implements Callable{  
      @Override  
      public Object onCall(MuleEventContext eventContext) throws Exception {  
           Object payload = eventContext.getMessage().getPayload();  
           DBObject thedata = (DBObject) JSON.parse((String) payload);  
           //db.getCollection("mycollection").insert(thedata, WriteConcern.SAFE);  
        ObjectId id = (ObjectId) thedata.get("_id");  
     if (id == null) return null;  
     return id.toStringMongod();  
      }  
 }