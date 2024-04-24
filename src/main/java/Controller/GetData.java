package Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;

@RestController
public class GetData {
    @GetMapping("api.coincap.io/v2/assets")
    public void getData() throws IOException {
        String url = "https://api.coincap.io/v2/assets";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        System.out.println(response);



}
    }
