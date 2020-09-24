import React from "react";
import { Platform, StyleSheet } from "react-native";
import {
  AnimatedImage,
  AnimatedText,
  AnimatedView,
  COLOR,
} from "../../utils/constants";
import { useSpring } from "react-spring";

const LoginTitle = ({ resetAnimation }) => {
  const titleOpacity = useSpring({
    config: {
      duration: 800,
    },
    opacity: 1,
    from: {
      opacity: 0,
    },
    reset: resetAnimation,
  });

  const titleMargin = useSpring({
    config: {
      duration: 1000,
    },
    delay: 400,
    marginTop: 0,
    marginBottom: 150,
    from: {
      marginTop: 100,
      marginBottom: 0,
    },
    reset: resetAnimation,
  });
  return (
    <AnimatedView
      style={{
        justifyContent: "center",
        alignItems: "center",
        marginBottom: titleMargin.marginBottom,
        marginTop: titleMargin.marginTop,
      }}
    >
      <AnimatedImage
        style={{ ...styles.titleIcon, opacity: titleOpacity.opacity }}
        source={require("../../assets/icon.png")}
      />
      <AnimatedText style={{ ...styles.title, opacity: titleOpacity.opacity }}>
        Peloton
      </AnimatedText>
    </AnimatedView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  titleIcon: {
    width: 80,
    height: 80,
    resizeMode: "contain",
  },
  title: {
    height: 45,
    fontSize: 35,
    fontWeight: "bold",
    fontStyle: "normal",
    fontFamily:
      Platform.OS === "ios" ? "AppleSDGothicNeo-UltraLight" : "normal",
    lineHeight: 38,
    letterSpacing: 0.9,
    color: COLOR.WHITE,
  },
});

export default LoginTitle;
