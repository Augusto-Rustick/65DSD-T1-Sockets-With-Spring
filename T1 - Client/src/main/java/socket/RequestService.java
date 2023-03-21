package Socket;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class RequestService {

    private String[] arrayDados;
    private String[] jsonKeys;
    private String baseUrl = "http://localhost:8080/";

    public RequestService(String request) throws Exception {
        arrayDados = request.split(";");
    }

    public String execute() {
        try {
            return this.requesting();
        } catch (Exception ex) {
            Logger.getLogger(RequestService.class.getName()).log(Level.SEVERE, null, ex);
            return "Erro ao tentar fazer a consulta";
        }
    }

    private String requesting() {
        String responseBody = "";
        String formatedUrl = baseUrl + arrayDados[0].toLowerCase() + "/" + arrayDados[1].toLowerCase();
        try ( CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            String methodName = "request" + arrayDados[1].toUpperCase();
            Method method = this.getClass().getMethod(methodName, CloseableHttpClient.class, String.class);
            responseBody = (String) method.invoke(this, httpClient, formatedUrl);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return responseBody;
    }

    public String requestGET(CloseableHttpClient httpClient, String formatedUrl) throws IOException {
        formatedUrl += "/" + arrayDados[2];
        HttpGet request = new HttpGet(formatedUrl);
        var response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
    }

    public String requestLIST(CloseableHttpClient httpClient, String formatedUrl) throws IOException {
        HttpGet request = new HttpGet(formatedUrl);
        var response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }

    public String requestREMOVE(CloseableHttpClient httpClient, String formatedUrl) throws IOException {
        formatedUrl += "/" + arrayDados[2];
        HttpDelete request = new HttpDelete(formatedUrl);
        var response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
    }

    public String requestINSERT(CloseableHttpClient httpClient, String formatedUrl) throws IOException {
        return this.requestINSERTandUPDATE(httpClient, formatedUrl, false);
    }

    public String requestUPDATE(CloseableHttpClient httpClient, String formatedUrl) throws IOException {
        return this.requestINSERTandUPDATE(httpClient, formatedUrl, true);
    }

    private String requestINSERTandUPDATE(CloseableHttpClient httpClient, String formatedUrl, boolean isUpdate) throws IOException {
        formatedUrl = baseUrl + arrayDados[0].toLowerCase() + "/insertOrUpdate";
        HttpPost request = new HttpPost(formatedUrl);
        JSONObject json = this.getJson(isUpdate);
        StringEntity params = new StringEntity(json.toString());
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        var response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
    }

    private JSONObject getJson(boolean isUpdate) {
        JSONObject json = new JSONObject();
        this.renderJsonKeys();
        int forInteration = this.getJsonForInteration(isUpdate);
        for (int c = 2; c < forInteration; c++) {
            json.put(jsonKeys[c], arrayDados[c]);
        }
        return json;
    }
    
    private void renderJsonKeys(){
        switch (arrayDados[0]) {
            case "cliente" ->
                jsonKeys = new String[]{"entity", "requestType", "cpf", "nome", "endereco", "telefone", "email", "id"};
            case "funcionario" ->
                jsonKeys = new String[]{"entity", "requestType", "cpf", "nome", "endereco", "ctps", "quantidadeVendas", "id"};
            case "departamento" ->
                jsonKeys = new String[]{"entity", "requestType", "nome", "produto", "quantidadeEstoque", "id"};
        }
    }
    
    private int getJsonForInteration(boolean isUpdate){
        int forInteration = arrayDados[0].equalsIgnoreCase("departamento") ? 5 : 7;
        if (isUpdate) {
            forInteration++;
        }
        return forInteration;
    }
}
