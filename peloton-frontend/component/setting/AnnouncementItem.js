import React from "react";
import { Alert, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { COLOR, questionEmail } from "../../utils/constants";
import * as Linking from "expo-linking";

const AnnouncementItem = ({ children, target }) => {
  const onPress = async () => {
    try {
      await Linking.openURL(`mailto:${questionEmail}`);
    } catch (e) {
      Alert.alert(
        "",
        `Mail 앱이 열리지 않습니다. ${questionEmail}로 문의 주세요.`
      );
    }
  };

  return (
    <TouchableOpacity style={styles.container} onPress={onPress}>
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
