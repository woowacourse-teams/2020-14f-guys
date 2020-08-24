import React from "react";
import { ScrollView, StyleSheet, Text } from "react-native";
import AnnouncementItem from "./AnnouncementItem";
import { COLOR } from "../../utils/constants";

const Announcement = () => {
  return (
    <ScrollView style={styles.container}>
      <AnnouncementItem target={"Null"}>공지 사항</AnnouncementItem>
      <AnnouncementItem target={"Null"}>1:1 문의</AnnouncementItem>
      <AnnouncementItem target={"License"}>
        오픈소스 라이선스 확인하기
      </AnnouncementItem>
      <AnnouncementItem target={"Null"}>현재 버전 1.0.0</AnnouncementItem>
      <Text style={styles.copyright}>
        Copyright 14f-Guys of Woowahan-techcourse-teams
      </Text>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: 20,
  },
  copyright: {
    marginVertical: 30,
    fontSize: 12,
    color: COLOR.GRAY1,
    fontWeight: "200",
    textAlign: "center",
  },
});

export default Announcement;
