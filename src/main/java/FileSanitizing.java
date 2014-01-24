
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.mule.api.MuleMessage;
import org.mule.api.annotations.ContainsTransformerMethods;
import org.mule.api.annotations.Transformer;
import org.mule.api.annotations.param.OutboundHeaders;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.config.i18n.Message;
import org.mule.transformer.AbstractMessageTransformer;

public class FileSanitizing extends AbstractMessageTransformer{

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		StringBuffer result = new StringBuffer();
		
		InputStream inputStream = (InputStream) message.getPayload();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        int lineNo = 1;
		String line;
		
        try {
			while ((line = br.readLine()) != null) {
				if(lineNo == 1){
					Map<String, Object> map = new HashMap<>();
					map.put("City", getCity(line));
					message.addProperties(map, PropertyScope.OUTBOUND);
				}
				if(lineNo > 1 && lineNo < 476){
					result.append(clearWhiteSpacesInData(extractCityData(line)));
					result.append("\n");
				}
				lineNo++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return new ByteArrayInputStream(result.toString().getBytes());
	}

	private String clearWhiteSpacesInData(String line) {
		return line.replaceAll(",\\s+", ",");
	}

	// get only first 9 columns
	private String extractCityData(String line) {
		Pattern pattern = Pattern.compile("^((?:[^,]*,){8}[^,]*),.+");
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
		    return matcher.group(1);
		} else {
			return line;
		}
	}

	private String getCity(String line) {
		String city = line.replaceAll(",", "").replaceAll("\\s*-.*", "");
		return deAccent(city);
	}
	
	public String deAccent(String str) {
	    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
	    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	    return pattern.matcher(nfdNormalizedString).replaceAll("");
	}

}
