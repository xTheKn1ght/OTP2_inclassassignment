FROM openjdk:17-slim

RUN apt-get update && apt-get install -y \
    wget unzip libgtk-3-0 libx11-6 libxext6 libxrender1 libxtst6 libxi6 \
    fonts-noto-cjk fonts-ipafont \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY target/ShoppingCartProject-1.0-SNAPSHOT.jar .

RUN wget https://download2.gluonhq.com/openjfx/21.0.2/openjfx-21.0.2_linux-x64_bin-sdk.zip && \
    unzip openjfx-21.0.2_linux-x64_bin-sdk.zip && rm openjfx-21.0.2_linux-x64_bin-sdk.zip

ENV PATH_TO_FX=/app/javafx-sdk-21.0.2/lib
ENV DISPLAY=:0

CMD ["java", "--module-path", "/app/javafx-sdk-21.0.2/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "ShoppingCartProject-1.0-SNAPSHOT.jar"]
