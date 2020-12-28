package Main.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by max on 29/07/2017.
 */
public class main extends Application {
    public void start(Stage primaryStage) throws Exception {
        String gClientId = "3234235481-titaorfhumkeh1556fq59e4ts99v82cf.apps.googleusercontent.com";
        String gRedir = "http://localhost:8080";
        String gScope = "https://www.googleapis.com/auth/userinfo.profile";
        String gSecret = "MEE8hwg259ajX0pY4MkFfl1C";
        OAuthAuthenticator auth = new OAuthGoogleAuthenticator(gClientId, gRedir, gSecret, gScope);
        auth.startLogin();
        
       // System.out.println(t);

//        String FACEBOOK_clientID = "309315746974708";
//        String FACEBOOK_redirectUri = "http://localhost:8080/";
//        String FACEBOOK_fieldsString = "name,gender,id";
//        String FACEBOOK_clientSecret = "2c04612d78ff9862e7b284aac572967d";
//
//        OAuthAuthenticator authFB = new OAuthFacebookAuthenticator(FACEBOOK_clientID, FACEBOOK_redirectUri, FACEBOOK_clientSecret, FACEBOOK_fieldsString);
//        authFB.startLogin();
//            authFB.getJsonData();



//
//        String GIT_clientID = "8169e2b715a16bfaad06";
//        String GIT_redirectUri = "http://localhost:8080";
//        String GIT_scope = "user";
//        String GIT_clientSecret = "d759a2d923149bd904fd13a4bc13c02117aaf736";
//
//        OAuthAuthenticator authGit = new OAuthGithubAuthenticator(GIT_clientID, GIT_redirectUri, GIT_clientSecret, GIT_scope);
//        //authGit.startLogin();


    }


}
