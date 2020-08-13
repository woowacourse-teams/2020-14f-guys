import React from "react";
import { Clipboard, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { Feather } from "@expo/vector-icons";
import { COLOR, DEEP_LINK_BASE_URL } from "../../../utils/constants";

const LinkCopyButton = ({ raceId }) => {
  const copyLink = async () => {
    Clipboard.setString(`${DEEP_LINK_BASE_URL}races/${raceId}`);
    alert("클립보드에 복사되었습니다.");
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
    width: 125,
    height: 50,
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: COLOR.BLUE3,
    borderRadius: 15,
  },
  copyText: {
    color: COLOR.WHITE,
    fontSize: 15,
  },
});

export default LinkCopyButton;
