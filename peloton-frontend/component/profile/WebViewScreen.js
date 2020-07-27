import React from "react";
import { StyleSheet, View } from "react-native";
import WebView from "react-native-webview";
import { useNavigation } from "@react-navigation/core";
import { SERVER_BASE_URL } from "../../utils/constants";
import { useRecoilState } from "recoil/dist";
import { userTokenState } from "../atoms";

const WebViewScreen = () => {
  const navigation = useNavigation();
  const [token, setToken] = useRecoilState(userTokenState);

  const onWebViewMessage = (event) => [
    console.log("onWebViewMessage", JSON.parse(event.nativeEvent.data)),
  ];

  const onNavigationStateChange = (webViewState) => {
    const url = webViewState.url;
    let accessToken;
    if (url.split("?").length <= 1) {
      return;
    }
    url
      .split("?")[1]
      .split("&")
      .forEach((param) => {
        const key = param.split("=")[0];
        if (key === "access_token") {
          accessToken = param.split("=")[1];
        }
      });

    if (accessToken) {
      setToken(accessToken);
      navigation.navigate("Profile");
    }
  };

  return (
    <View style={{ flex: 1 }}>
      <WebView
        useWebKit={true}
        source={{ uri: `${SERVER_BASE_URL}/api/login` }}
        style={{ marginTop: 10 }}
        startInLoadingState={true}
        incognito={true}
        onNavigationStateChange={onNavigationStateChange}
        onMessage={onWebViewMessage}
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
