import React from "react";
import { Image, StyleSheet, TouchableOpacity, View } from "react-native";
import WebView from "react-native-webview";
import { SERVER_BASE_URL } from "../../utils/constants";
import { CommonActions } from "@react-navigation/native";
import { useSetRecoilState } from "recoil";
import { userTokenState } from "../atoms";
import { useNavigation } from "@react-navigation/core";

const KakaoLoginWebView = ({ toggleModal }) => {
  const navigation = useNavigation();
  const setToken = useSetRecoilState(userTokenState);

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
      alert("알 수 없는 오류가 발생했습니다.");
      toggleModal();
    }

    if (accessToken) {
      setToken(accessToken);
      toggleModal();
      if (isCreated) {
        navigation.navigate("ChangeNickname");
      } else {
        navigation.dispatch({
          ...CommonActions.reset({
            index: 0,
            routes: [
              {
                name: "Root",
              },
            ],
          }),
        });
      }
    }
  };

  return (
    <>
      <View style={styles.container}>
        <TouchableOpacity
          style={{ height: 30, marginTop: 40, marginLeft: 10 }}
          onPress={toggleModal}
        >
          <Image
            style={{ width: 25, height: 25, opacity: 0.5, padding: 10 }}
            source={require("../../assets/close-512.png")}
          />
        </TouchableOpacity>
      </View>
      <View style={{ flex: 8 }}>
        <WebView
          useWebKit={true}
          source={{ uri: `${SERVER_BASE_URL}/api/login` }}
          scalesPageToFit
          startInLoadingState={true}
          incognito={true}
          onNavigationStateChange={onNavigationStateChange}
          onError={() => console.log("에러!!")}
          onHttpError={() => console.log("http에러!!")}
        />
      </View>
    </>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#21365d",
    justifyContent: "center",
    alignItems: "flex-start",
  },
});

export default KakaoLoginWebView;
