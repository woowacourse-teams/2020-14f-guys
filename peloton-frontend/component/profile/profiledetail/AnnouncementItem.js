import React from "react";
import { StyleSheet, Text, TouchableOpacity } from "react-native";
import { useNavigation } from "@react-navigation/core";

const AnnouncementItem = ({ children }) => {
  const navigation = useNavigation();

  return (
    <TouchableOpacity
      style={styles.container}
      onPress={() => navigation.navigate("License")}
    >
      <Text style={styles.title}>{children}</Text>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginLeft: 16,
  },
  title: {
    fontSize: 30,
    marginVertical: 30,
  },
});

export default AnnouncementItem;
