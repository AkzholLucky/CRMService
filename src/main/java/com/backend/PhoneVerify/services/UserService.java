package com.backend.PhoneVerify.services;

import com.backend.PhoneVerify.models.User;
import com.backend.PhoneVerify.repositories.UsersRepository;
import com.backend.PhoneVerify.utils.smsc_api;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class UserService {
    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public Boolean findUserByNameAndNumber(String name, String number) throws IOException {
        User user = usersRepository.findByNameAndNumber(name, number).orElse(null);

//        smsc_api smsc_api = new smsc_api("luckyman03", "sjwuQ4sRSLny25G");

        if (user != null) {
            int code = (int) (1000 + Math.random() * 8999);

            user.setActivationCode(Integer.toString(code));

            String message = generateMessage(user);
            sendMessage(message, user.getNumber());
//            smsc_api.send_sms(user.getNumber(), message, 0, "", "", 0, "", "");
            return true;
        }

        return false;
    }

    public User findByCode(String code) {
        Optional<User> user = usersRepository.findByActivationCode(code);

        return user.orElse(null);
    }

    @Transactional
    public void findByCodeAndSave(String code, User user) {
        User existsUser = findByCode(code);
        existsUser.setPassword(user.getPassword());
        existsUser.setActivationCode(null);
    }

    public void sendMessage(String message, String number) throws IOException {
        URL url = new URL("https://api.mobizon.kz/service/message/sendsmsmessage?recipient=" + number + "&text=" + message + "&apiKey=kze91f455464a8ca31bcdec9f0b45d0e164a328735e4ffa38bf8d227cec42675b92133");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("Response code: " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println("Response body: " + response.toString());
    }

    public String generateMessage(User user) {
        return String.format(
                "Code:%s",
                user.getActivationCode()
        );
    }
}
