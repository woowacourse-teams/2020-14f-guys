import React from "react";
import { Alert, Clipboard, StyleSheet, Text, TouchableOpacity, View, } from "react-native";
import { Feather } from "@expo/vector-icons";
import { COLOR } from "../../../utils/constants";
import { raceShareLink } from "./RaceDeepLinkPage";

const LinkCopyButton = ({ raceId }) => {
  const copyLink = async () => {
    Clipboard.setString(raceShareLink(raceId));
    Alert.alert("", "클립보드에 복사되었습니다.");
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.copyButton} onPress={copyLink}>
        <Text style={styles.copyText}>링크 복사하기</Text>
        <Feather name="clipboard" size={24} color={COLOR.WHITE} />
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    height: 100,
    justifyContent: "center",
    alignItems: "center",
  },
  copyButton: {
    paddingVertical: 10,
    paddingHorizontal: 25,
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: COLOR.GRAY1,
    borderRadius: 30,
  },
  copyText: {
    color: COLOR.WHITE,
    fontSize: 15,
  },
});

export default LinkCopyButton;
