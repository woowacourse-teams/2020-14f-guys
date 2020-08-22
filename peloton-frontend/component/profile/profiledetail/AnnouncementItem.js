import React from "react";
import { StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { useNavigation } from "@react-navigation/core";
import { COLOR } from "../../../utils/constants";

const AnnouncementItem = ({ children, target }) => {
  const navigation = useNavigation();

  return (
    <TouchableOpacity
      style={styles.container}
      onPress={() => navigation.navigate(target)}
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
    marginTop: 20,
  },
  item: {
    borderBottomWidth: 1,
    borderColor: COLOR.GRAY3,
    width: "80%",
  },
  title: {
    fontSize: 23,
    textAlign: "center",
    paddingBottom: 10,
  },
});

export default AnnouncementItem;
