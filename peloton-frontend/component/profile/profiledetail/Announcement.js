import React from "react";
import { ScrollView, StyleSheet } from "react-native";
import AnnouncementItem from "./AnnouncementItem";

const Announcement = () => {
  return (
    <ScrollView style={styles.container}>
      <AnnouncementItem target={"License"}>오픈소스 라이선스</AnnouncementItem>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default Announcement;
