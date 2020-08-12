import React from "react";
import { StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { MaterialCommunityIcons } from "@expo/vector-icons";
import { useNavigation } from "@react-navigation/native";

import { COLOR, SAMPLE_SUBTITLE } from "../../../utils/constants";
import { useRecoilValue } from "recoil";
import { memberInfoState } from "../../../state/member/MemberState";

const HomeBanner = () => {
  const navigation = useNavigation();
  const userInfo = useRecoilValue(memberInfoState);

  const goToCategorySelection = () => {
    navigation.navigate("CategorySelection");
  };

  const subtitle =
    SAMPLE_SUBTITLE[Math.floor(Math.random() * SAMPLE_SUBTITLE.length)];

  return (
    <View style={styles.container}>
      <View style={styles.bannerTop}>
        <Text style={styles.title}>{userInfo.name}님,</Text>
        <Text style={styles.subtitle}>{subtitle}</Text>
        <View style={styles.bannerSeparator} />
      </View>
      <TouchableOpacity
        activeOpacity={0.7}
        style={styles.raceStartButton}
        onPress={goToCategorySelection}
      >
        <Text style={styles.raceStart}>새로운 레이스를 시작하세요.</Text>
        <View style={styles.raceStartIconContainer}>
          <MaterialCommunityIcons
            name="chevron-triple-right"
            size={24}
            color={COLOR.WHITE}
          />
        </View>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLOR.GREEN1,
    justifyContent: "center",
    paddingHorizontal: 35,
  },
  title: {
    fontSize: 30,
    fontWeight: "600",
    color: COLOR.WHITE,
    marginTop: 46,
    marginBottom: 5,
  },
  subtitle: {
    fontSize: 24,
    fontWeight: "500",
    color: COLOR.WHITE,
    marginBottom: 19,
    lineHeight: 35,
  },
  raceStartButton: {
    flexDirection: "row",
    alignItems: "center",
    flex: 1,
    marginBottom: 10,
  },
  raceStart: {
    fontSize: 18,
    fontWeight: "300",
    color: COLOR.WHITE,
  },
  bannerSeparator: {
    position: "absolute",
    bottom: 0,
    left: 0,
    borderBottomWidth: 1,
    width: 33,
    borderBottomColor: COLOR.WHITE,
  },
  raceStartIconContainer: {
    marginLeft: 30,
  },
});

export default HomeBanner;
