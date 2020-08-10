import React from "react";
import { Share, StyleSheet, TouchableOpacity } from "react-native";
import { EvilIcons } from "@expo/vector-icons";
import { COLOR } from "../../../utils/constants";

const ShareButton = () => {
  const onShare = async () => {
    await Share.share({
      message:
        "React Native | A framework for building native apps using React",
    });
  };

  return (
    <TouchableOpacity style={styles.shareButton} onPress={onShare}>
      <EvilIcons name="share-apple" size={44} color={COLOR.BLACK} />
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  shareButton: {
    paddingRight: 15,
  },
});

export default ShareButton;
