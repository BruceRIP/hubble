package org.budgie.hubble.context.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.budgie.hubble.context.exceptions.RegistryException;
import org.budgie.hubble.context.exceptions.ResponseException;
import org.budgie.hubble.properties.InstanceProperties;
import org.budgie.hubble.properties.vo.Connection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class RestInvoker {

    public void register(final Connection connectionService, final String method, final InstanceProperties instanceProperties) throws RegistryException, ResponseException {
        if(instanceProperties == null){
            throw new RegistryException("Instance properties cannot be null, please very your config file");
        }
        URL url = null;
        try {
            url = buildURLRegisterService(connectionService);
        } catch (MalformedURLException e) {
            throw new RegistryException("MalformedURL", e);
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RegistryException("OpenConnection", e);
        }
        try {
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setReadTimeout(5000);
            connection.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(Objects.requireNonNull(buildRequestRegisterService(instanceProperties)));
            if(connection.getResponseCode() < 200 || connection.getResponseCode() > 300) {
                throw new ResponseException("Error reponse code " + connection.getResponseCode() +" from " + url);
            }
        } catch (ProtocolException e) {
            throw new RegistryException("Protocol", e);
        }catch(IOException e) {
            throw new RegistryException("NoResponse", e);
        }
    }

    private String buildRequestRegisterService(final InstanceProperties instanceProperties){
        Map<String, Object> request = new LinkedHashMap<>();
        StringBuilder builder = null;
        if(StringUtils.isNotBlank(instanceProperties.getHost())) {
            builder = new StringBuilder(instanceProperties.getSchema())
                    .append("://")
                    .append(instanceProperties.getHost())
                    .append(":")
                    .append(instanceProperties.getPort())
                    .append("/")
                    .append(instanceProperties.getPath());
        }else {
            builder = new StringBuilder(instanceProperties.getUri());
        }
        try {
            URL url = new URL(builder.toString());
            request.put("schema", url.getProtocol());
            request.put("ip", url.getHost());
            request.put("port", url.getPort());
            request.put("service", instanceProperties.getInstanceName());
            request.put("url", builder.toString());
            return new ObjectMapper().writeValueAsString(request);
        } catch (JsonProcessingException | MalformedURLException e) {
            return null;
        }
    }

    private URL buildURLRegisterService(final Connection connection)throws MalformedURLException{
        StringBuilder builder;
        if(StringUtils.isNotBlank(connection.getHost())) {
            builder = new StringBuilder(connection.getSchema())
                    .append("://")
                    .append(connection.getHost())
                    .append(":")
                    .append(connection.getPort())
                    .append("/")
                    .append(connection.getPath());
        }else {
            builder = new StringBuilder(connection.getUri());
        }
        return new URL(builder.toString());
    }
}
