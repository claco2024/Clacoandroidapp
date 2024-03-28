package claco.store.APi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class LenientGsonConverterFactory extends Converter.Factory {
    private final Gson gson;

    private LenientGsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    public static LenientGsonConverterFactory create() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new LenientGsonConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(
            java.lang.reflect.Type type,
            java.lang.annotation.Annotation[] annotations,
            Retrofit retrofit) {
        return new LenientGsonResponseBodyConverter<>(gson, type);
    }

    private static class LenientGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final java.lang.reflect.Type type;

        LenientGsonResponseBodyConverter(Gson gson, java.lang.reflect.Type type) {
            this.gson = gson;
            this.type = type;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            try {
                String json = value.string();
                return gson.fromJson(json, type);
            } catch (JsonParseException e) {
                // You can handle the JsonParseException here if needed
                throw new IOException("Failed to parse JSON", e);
            } finally {
                value.close();
            }
        }
    }
}
