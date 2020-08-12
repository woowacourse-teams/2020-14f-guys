import React from "react";
import { StyleSheet, View } from "react-native";
import WebView from "react-native-webview";
import { COLOR, SERVER_BASE_URL } from "../../utils/constants";
import { useSetRecoilState } from "recoil";
import { useNavigation } from "@react-navigation/core";
import { navigateWithoutHistory } from "../../utils/util";
import WebViewCloseButton from "./WebViewCloseButton";
import { memberTokenState } from "../../state/member/MemberState";

const KakaoLoginWebView = ({ toggleModal }) => {
  const navigation = useNavigation();
  const setToken = useSetRecoilState(memberTokenState);

  const onNavigationStateChange = (webViewState) => {
    const url = webViewState.url;
    let accessToken;
    let isCreated = true;
    let success = true;

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
        } else if (key === "is_created") {
          isCreated = param.split("=")[1];
        } else if (key === "success") {
          success = param.split("=")[1];
        }
      });
    if (!success) {
      console.log("login Fail");
      toggleModal();
      return;
    }
    if (accessToken) {
      toggleModal();
      setToken(accessToken);
      if (isCreated === "true") {
        navigation.navigate("ChangeNickname");
      } else {
        navigateWithoutHistory(navigation, "ApplicationNavigationRoot");
      }
    }
  };

  return (
    <>
      <View style={styles.webviewCloseButton}>
        <WebViewCloseButton toggleModal={toggleModal} />
      </View>
      <View style={styles.webview}>
        <WebView
          useWebKit={true}
          source={{ uri: `${SERVER_BASE_URL}/api/login` }}
          scalesPageToFit
          startInLoadingState={true}
          incognito={true}
          onNavigationStateChange={onNavigationStateChange}
        />
      </View>
    </>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLOR.BLUE5,
    justifyContent: "center",
    alignItems: "flex-start",
  },
  webviewCloseButton: {
    height: 35,
    marginTop: 40,
    marginLeft: 10,
  },
  webview: {
    flex: 1,
  },
});

export default KakaoLoginWebView;
