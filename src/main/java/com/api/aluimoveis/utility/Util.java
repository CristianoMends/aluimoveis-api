package com.api.aluimoveis.utility;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component()
public class Util {
    @Autowired
    private Environment env;

    @Value("${azure.blob.token}")
    String token;
    @Value("${azure.blob.url}")
    String url;
    @Value("${azure.blob.container}")
    String container;

    /**
     * Faz o upload de um arquivo para o Azure Blob Storage.
     * Retorna a url do arquivo no Azure
     */
    public String uploadFile(MultipartFile file) {
        String blobUrl = null;

        try {

            String blobName = file.getOriginalFilename();

            if (token != null && url != null && container != null) {
                BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                        .endpoint(url)
                        .sasToken(token)
                        .buildClient();

                BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(container);
                BlobClient blobClient = containerClient.getBlobClient(blobName);

                try {

                    blobClient.upload(file.getInputStream(), file.getSize(), true);
                    blobClient.setHttpHeaders(new BlobHttpHeaders().setContentType(file.getContentType()));
                    blobUrl = blobClient.getBlobUrl();

                } catch (IOException e) {
                    return null;
                }
            }
        }catch (Exception e){
            e.getStackTrace();
        }
        return blobUrl;
    }

    /**
     * Delete um blob do azure.
     * Retorna verdadeiro se sucesso, se não, falso
     * */
    public boolean deleteFile(String blobUrl) {

        boolean success = false;
        if (this.token != null && this.url != null && this.container != null) {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .endpoint(this.url)
                    .sasToken(this.token)
                    .buildClient();

            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(container);

            String[] splitUrl = blobUrl.split("/");//divide a url por '/'
            String blobName = splitUrl[splitUrl.length - 1];//obtem o nome do blob

            BlobClient blobClient = containerClient.getBlobClient(blobName);

            success = blobClient.deleteIfExists();
        }
        return success;
    }
}
