import React from "react";
import { ActivityIndicator, Alert, StyleSheet, View } from "react-native";
import { useRecoilState, useRecoilValue, useResetRecoilState } from "recoil";
import { useNavigation } from "@react-navigation/native";

import RaceCreateUnit from "./RaceCreateUnit";
import { raceCreateInfoState } from "../../../state/race/RaceState";
import { COLOR } from "../../../utils/constants";
import { loadingState } from "../../../state/loading/LoadingState";
import LoadingIndicator from "../../../utils/LoadingIndicator";
import RaceCreateView from "./RaceCreateView";
import {
  alertNotEnoughCash,
  navigateTabScreen,
  navigateWithHistory,
} from "../../../utils/util";
import {
  memberInfoState,
  memberTokenState,
} from "../../../state/member/MemberState";
import { RaceApi } from "../../../utils/api/RaceApi";
import { MemberApi } from "../../../utils/api/MemberApi";
import { RiderApi } from "../../../utils/api/RiderApi";

const InputRaceFee = () => {
  // eslint-disable-next-line prettier/prettier
  const {title, description, start_date, end_date, category, entrance_fee, mission_start_time, mission_end_time, days} = useRecoilValue(raceCreateInfoState);
  const resetRaceCreateInfo = useResetRecoilState(raceCreateInfoState);
  const [loading, setGlobalLoading] = useRecoilState(loadingState);
  const token = useRecoilValue(memberTokenState);
  const [memberInfo, setMemberInfo] = useRecoilState(memberInfoState);
  const navigation = useNavigation();

  const formatPostRaceBody = () => {
    return {
      title,
      description,
      category,
      entrance_fee,
      race_duration: {
        start_date,
        end_date,
      },
      days,
      certification_available_duration: {
        start_time: mission_start_time,
        end_time: mission_end_time,
      },
    };
  };

  const createRaceRequest = async () => {
    setGlobalLoading(true);
    try {
      const { location } = await RaceApi.post(token, formatPostRaceBody());
      const race_id = location.split("/")[3];
      await RiderApi.post(token, race_id);
      resetRaceCreateInfo();
      navigateWithHistory(navigation, [
        { name: "Home" },
        {
          name: "RaceDetail",
          params: { id: race_id },
        },
      ]);
    } catch (e) {
      alert(e.toString());
    }
    setGlobalLoading(false);
  };

  const submitRaceRequest = async () => {
    const userCash = Number(memberInfo.cash);

    if (!entrance_fee) {
      alert("입장료를 입력해주세요");
      return;
    }
    if (entrance_fee < 0) {
      alert("입장료는 음수가 될 수 없습니다");
      return;
    }

    if (userCash < entrance_fee) {
      alertNotEnoughCash({
        onOk: () => navigateTabScreen(navigation, "Profile"),
      });
      setGlobalLoading(false);
      return;
    }
    try {
      const newMemberInfo = await MemberApi.get(token);
      setMemberInfo(newMemberInfo);
      await createRaceRequest();
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <LoadingIndicator>
      <RaceCreateView onPress={submitRaceRequest}>
        <RaceCreateUnit postfix="원" fieldName="entrance_fee" number>
          Race의 입장료를 결정해주세요
        </RaceCreateUnit>
      </RaceCreateView>
      {loading && (
        <View style={styles.loadingIndicator}>
          <ActivityIndicator size="large" color={COLOR.GRAY5} />
        </View>
      )}
    </LoadingIndicator>
  );
};

const styles = StyleSheet.create({
  subjectContainer: {
    marginBottom: 120,
  },
});

export default InputRaceFee;
