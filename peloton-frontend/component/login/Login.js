import React, { useState } from "react";
import {
  Modal,
  SafeAreaView,
  StyleSheet,
  TouchableOpacity,
  View,
} from "react-native";
import KakaoLoginWebView from "./KakaoLoginWebView";
import LoginTitle from "./LoginTitle";
import * as AppleAuthentication from "expo-apple-authentication";
import { animated, useSpring } from "react-spring";
import {
  AnimatedImage,
  SERVER_BASE_URL,
  TOKEN_STORAGE,
} from "../../utils/constants";
import AsyncStorage from "@react-native-community/async-storage";
import { useSetRecoilState } from "recoil";
import { userInfoState, userTokenState } from "../atoms";
import { CommonActions } from "@react-navigation/native";
import { useNavigation } from "@react-navigation/core";
import Axios from "axios";
import LoadingIndicator from "../../utils/LoadingIndicator";
import { loadingState } from "../../state/loading/LoadingState";

const AnimatedAppleButton = animated(
  AppleAuthentication.AppleAuthenticationButton,
);

const Login = () => {
  const [modalVisible, setModalVisible] = useState(false);
  const setToken = useSetRecoilState(userTokenState);
  const setUserInfo = useSetRecoilState(userInfoState);
  const navigation = useNavigation();
  const setIsLoading = useSetRecoilState(loadingState);

  const toggleModal = () => {
    setModalVisible(!modalVisible);
  };

  const navigateHome = () => {
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
  };

  const onLogin = async () => {
    setIsLoading(true);
    const token = await AsyncStorage.getItem(TOKEN_STORAGE);
    if (token) {
      setToken(token);
      const response = await Axios({
        method: "GET",
        baseURL: SERVER_BASE_URL,
        url: "/api/members",
        headers: {
          Authorization: "Bearer " + token,
        },
      }).catch(() => {
        toggleModal();
        setIsLoading(false);
      });
      if (response.status === 200) {
        setUserInfo(response.data);
        navigateHome();
      }
    } else {
      toggleModal();
    }
    setIsLoading(false);
  };

  const buttonOpacity = useSpring({
    config: {
      duration: 800,
    },
    delay: 1200,
    opacity: 1,
    from: {
      opacity: 0,
    },
  });

  return (
    <LoadingIndicator>
      <SafeAreaView style={styles.background}>
        <Modal animationType={"slide"} visible={modalVisible} transparent>
          <KakaoLoginWebView toggleModal={toggleModal} />
        </Modal>
        <View style={styles.titleContainer}>
          <LoginTitle />
        </View>
        <View style={styles.loginButtonContainer}>
          <TouchableOpacity onPress={onLogin}>
            <AnimatedImage
              style={{
                ...styles.loginButton,
                opacity: buttonOpacity.opacity,
              }}
              source={require("../../assets/kakao_login_medium_wide.png")}
            />
          </TouchableOpacity>
          <AnimatedAppleButton
            buttonType={
              AppleAuthentication.AppleAuthenticationButtonType.SIGN_IN
            }
            buttonStyle={
              AppleAuthentication.AppleAuthenticationButtonStyle.BLACK
            }
            style={{ ...styles.loginButton, opacity: buttonOpacity.opacity }}
            onPress={() => alert("힝 속았지?")}
          />
        </View>
      </SafeAreaView>
    </LoadingIndicator>
  );
};

const styles = StyleSheet.create({
  background: {
    flex: 1,
    backgroundColor: "#21365d",
  },
  titleContainer: {
    flex: 7,
    height: 286,
    justifyContent: "center",
    alignItems: "center",
  },
  loginButtonContainer: {
    flex: 3,
    justifyContent: "center",
    alignItems: "center",
  },
  loginButton: {
    marginVertical: 5,
    justifyContent: "center",
    alignItems: "center",
    width: 300,
    height: 45,
    resizeMode: "contain",
  },
});

export default Login;
