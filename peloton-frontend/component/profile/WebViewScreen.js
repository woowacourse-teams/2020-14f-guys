import React from "react";
import { StyleSheet, View } from "react-native";
import WebView from "react-native-webview";

const WebViewScreen = () => {
  return (
    <View style={{ flex: 1 }}>
      <WebView
        useWebKit={true}
        source={{ uri: "https://2e710ef6be0c.ngrok.io/api/login" }}
        style={{ marginTop: 10 }}
        startInLoadingState={true}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default WebViewScreen;
