import React from "react";
import { StyleSheet, Text, View } from "react-native";
import RaceSubTitle from "./RaceSubTitle";

const RaceSpec = () => {
  return (
    <View style={styles.detailContainer}>
      <RaceSubTitle>레이스 세부정보</RaceSubTitle>
      <Text style={styles.detail}>
        ⭐️ 레이스 기간 : 2019.02.03 ~ 2020.03.20 {"\n"}
        ⭐️ 인증 주기 : 월, 수, 금 {"\n"}
        ⭐️ 인증 시간 : 17:00 ~ 23:00 {"\n"}
        ⭐️ 모인 금액 : 50000원
      </Text>
    </View>
  );
};

const styles = StyleSheet.create({
  detailContainer: {
    backgroundColor: "#F2F2F2",
    paddingBottom: 10,
    paddingHorizontal: 20,
  },
  detail: {
    fontSize: 15,
    color: "gray",
    fontWeight: "300",
    lineHeight: 23,
  },
});

export default RaceSpec;
