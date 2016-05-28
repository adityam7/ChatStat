package co.haptik;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class HaptikDaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "co.haptik.test.chatstat.model");

        Entity message = schema.addEntity("Message");
        message.addIdProperty();
        message.addStringProperty("body");
        message.addStringProperty("username");
        message.addBooleanProperty("favourite");
        message.addStringProperty("name")
                .codeBeforeField("@com.google.gson.annotations.SerializedName( \"Name\" )");
        message.addDateProperty("messageTime")
                .codeBeforeField("@com.google.gson.annotations.SerializedName( \"message-time\" )");
        message.addStringProperty("imageUrl")
                .codeBeforeField("@com.google.gson.annotations.SerializedName( \"image-url\" )");
        message.addLongProperty("timeStamp");
        schema.enableKeepSectionsByDefault();
        try {
            new DaoGenerator().generateAll(schema, "../app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
