import React from "react";
import { Alert, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { useNavigation } from "@react-navigation/core";
import { COLOR } from "../../utils/constants";

const AnnouncementItem = ({ children, target }) => {
  const navigation = useNavigation();

  return (
    <TouchableOpacity
      style={styles.container}
      onPress={() =>
        target !== "Null"
          ? navigation.navigate(target)
          : Alert.alert("", "아직 지원하지 않습니다 조금만 기다려주세요 😅")
      }
    >
      <View style={styles.item}>
        <Text style={styles.title}>{children}</Text>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
  },
  item: {
    borderBottomWidth: 1,
    borderTopWidth: 0.1,
    borderColor: COLOR.GRAY6,
    backgroundColor: COLOR.WHITE,
    shadowColor: COLOR.GRAY1,
    shadowOpacity: 0.05,
    shadowOffset: { width: 0, height: 2 },
    elevation: 3,
    paddingLeft: 25,
    width: "100%",
  },
  title: {
    fontSize: 18,
    paddingVertical: 18,
  },
});

export default AnnouncementItem;
