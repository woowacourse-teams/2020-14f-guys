import React from "react";
import ReadMore from "../../../utils/ReadMore";
import { Dimensions, ScrollView, StyleSheet, Text, View } from "react-native";
import RaceSpecItem from "./RaceSpecItem";
import { COLOR } from "../../../utils/constants";

const RaceJoinBody = ({ raceInfo, memberInfo }) => {
  return (
    <ScrollView style={styles.raceBody}>
      <ReadMore fontStyle={styles.raceDescription}>
        {raceInfo.description}
      </ReadMore>
      <View style={styles.border} />
      <View>
        <RaceSpecItem
          keyStyle={{ textAlign: "right" }}
          valueStyle={{ textAlign: "right" }}
          itemKey={"현재 캐시"}
          value={`${memberInfo.cash}원`}
          border={false}
        />
        <View style={styles.feeContainer}>
          <Text style={styles.minusIcon}>-</Text>
          <RaceSpecItem
            keyStyle={{ textAlign: "right" }}
            valueStyle={{ textAlign: "right" }}
            itemKey={"입장료"}
            value={`${raceInfo.entrance_fee}원`}
            border={false}
          />
        </View>
      </View>
      <View style={styles.calculateBorder} />
      <RaceSpecItem
        keyStyle={{ textAlign: "right" }}
        valueStyle={{ textAlign: "right" }}
        itemKey={"참여 후 금액"}
        value={`${memberInfo.cash - raceInfo.entrance_fee}원`}
        border={false}
      />
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  calculateBorder: {
    borderBottomWidth: 1,
    borderColor: COLOR.GRAY3,
    marginTop: 10,
    marginBottom: 25,
    width: Dimensions.get("window").width * 0.85,
  },
  raceBody: {
    paddingTop: 30,
    paddingLeft: 30,
    paddingRight: 30,
    flex: 5,
    backgroundColor: COLOR.WHITE,
  },
  border: {
    marginTop: 10,
    marginBottom: 35,
  },
  raceDescription: {
    fontSize: 20,
    fontWeight: "500",
    color: COLOR.BLACK,
  },
  feeContainer: {
    flexDirection: "row",
    alignItems: "center",
  },
  minusIcon: {
    paddingLeft: 30,
    fontSize: 30,
    fontWeight: "200",
    color: COLOR.GRAY8,
  },
});

export default RaceJoinBody;
