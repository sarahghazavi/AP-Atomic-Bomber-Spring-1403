package controller;

import com.google.gson.*;
import model.Data;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class UserController {
    public static boolean usernameExists(String username) {
        return Files.exists(Paths.get("src/main/resources/Players/" + username));
    }

    public static void makeUser(String username, String password) {
        String path = "src/main/resources/Players/" + username;
        File directory = new File(path);

        if (!directory.exists())
            if (!directory.mkdir()) return;

        Gson gson = new GsonBuilder().create();
        JsonObject details = new JsonObject();
        details.addProperty("password", password);
        details.addProperty("wave", 0);
        details.addProperty("kill", 0);
        details.addProperty("xKill", 0);
        details.addProperty("shoot", 0);
        details.addProperty("xShoot", 0);
        details.addProperty("music", true);
        details.addProperty("color", true);
        details.addProperty("arrow", true);
        details.addProperty("level", "easy");

        String userJson = gson.toJson(details);
        try (FileWriter writer = new FileWriter(path + "/info.json")) {
            writer.write(userJson);
        } catch (IOException e) {
            System.out.println("UserController : makeUser");
        }

        Path avatar = Paths.get(path + "/avatar.png");
        try {
            Files.createDirectories(avatar.getParent());
            Files.createFile(avatar);
        } catch (Exception e) {
            System.out.println("UserController : makeUser");
        }

        try {
            Path source = Paths.get(Data.getImage("av" + Data.getRandom(1, 5) + ".png").toURI());
            Files.createDirectories(avatar.getParent());
            Files.copy(source, avatar, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println("UserController : makeUser");
        }
    }

    public static String getField(String username, String field) {
        String path = "src/main/resources/Players/" + username + "/info.json";
        File file = new File(path);

        if (!file.exists()) {
            System.out.println("info.json not found");
            System.out.println("error for " + username + " in field " + field);
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            JsonObject userDetails = JsonParser.parseReader(reader).getAsJsonObject();
            JsonElement detail = userDetails.get(field);
            if (detail != null) {
                if (detail.isJsonPrimitive())
                    return detail.getAsString();
                else if (detail.isJsonArray())
                    return detail.getAsJsonArray().toString();
                else if (detail.isJsonObject())
                    return detail.getAsJsonObject().toString();
                else
                    return detail.toString();
            } else {
                System.out.println("error for " + username + " in field " + field);
                return null;
            }
        } catch (IOException e) {
            System.out.println("UserController : getField");
            return null;
        }
    }

    public static void setField(String username, String field, String value) {
        String path = "src/main/resources/Players/" + username + "/info.json";
        File file = new File(path);

        if (!file.exists()) {
            System.out.println("info.json for " + username + " doesn't exist!");
            return;
        }

        JsonObject userDetails;
        try (FileReader reader = new FileReader(file)) {
            userDetails = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            System.out.println("UserController : setField");
            return;
        }

        assert userDetails != null;
        try {
            JsonElement newValue = JsonParser.parseString(value);
            userDetails.add(field, newValue);
        } catch (JsonSyntaxException e) {
            // If value is not a valid JSON, treat it as a plain string
            userDetails.addProperty(field, value);
        }

        Gson gson = new GsonBuilder().create();
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(gson.toJson(userDetails));
        } catch (IOException e) {
            System.out.println("UserController : setField - IOException during write");
            e.printStackTrace();
        }
    }

    public static void setUsername(String oldName, String newName) {
        Path oldPath = Paths.get("src/main/resources/Players/" + oldName);
        Path newPath = Paths.get("src/main/resources/Players/" + newName);
        try {
            Files.move(oldPath, newPath);
        } catch (Exception e) {
            System.out.println("UserController : setUsername");
        }
    }

    public static boolean passwordMatch(String username, String password) {
        String mainPassword = getField(username, "password");
        assert mainPassword != null;
        return mainPassword.equals(password);
    }

    public static void deleteUser(String username) {
        Path path = Paths.get("src/main/resources/Players/" + username);
        try {
            Files.walk(path).sorted(java.util.Comparator.reverseOrder())
                    .map(Path::toFile).forEach(java.io.File::delete);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setAvatar(String username, int number) {
        Path destination = Paths.get("src/main/resources/Players/" + username + "/avatar.png");
        try {
            Path source = Paths.get(Objects.requireNonNull(UserController.class.getResource
                    ("/Images/av" + number + ".png")).toURI());
            Files.createDirectories(destination.getParent());
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}