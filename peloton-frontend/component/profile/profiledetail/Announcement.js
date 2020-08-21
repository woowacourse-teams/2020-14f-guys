import React from "react";
import { StyleSheet, Text, TouchableOpacity, View } from "react-native";
import AnnouncementItem from "./AnnouncementItem";

const Announcement = () => {
  return (
    <View style={styles.container}>
      <AnnouncementItem>오픈소스 라이선스</AnnouncementItem>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default Announcement;
