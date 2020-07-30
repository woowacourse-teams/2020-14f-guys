import React from "react";
import { View, StyleSheet, Image, TouchableOpacity } from "react-native";

const WebViewCloseButton = ({ toggleModal }) => {
  return (
    <TouchableOpacity onPress={toggleModal}>
      <Image
        style={styles.webviewCloseButtonImage}
        source={require("../../assets/close-512.png")}
      />
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  webviewCloseButtonImage: {
    width: 25,
    height: 25,
    opacity: 0.5,
    padding: 10,
  },
});

export default WebViewCloseButton;
