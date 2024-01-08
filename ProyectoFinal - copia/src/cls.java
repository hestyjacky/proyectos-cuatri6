import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

public class GoogleDriveUploader {
    private static final String APPLICATION_NAME = "Your Application Name";
    private static final String UPLOAD_FILE_PATH = "Path to Your File";
    private static final String MIME_TYPE = "application/octet-stream"; // Or appropriate MIME type

    public static void main(String[] args) {
        try {
            Drive service = initializeDriveService();
            uploadFileToDrive(service);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    private static Drive initializeDriveService() throws IOException, GeneralSecurityException {
        GoogleCredential credential = GoogleCredential.fromStream(
                        GoogleDriveUploader.class.getResourceAsStream("/path-to-your-credentials.json"))
                .createScoped(Collections.singleton(DriveScopes.DRIVE_FILE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                new GsonFactory(),
                credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static void uploadFileToDrive(Drive service) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(new File(UPLOAD_FILE_PATH).getName());

        java.io.File filePath = new java.io.File(UPLOAD_FILE_PATH);
        FileContent mediaContent = new FileContent(MIME_TYPE, filePath);

        File uploadedFile = service.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        System.out.println("File uploaded: " + uploadedFile.getId());
    }
}
