import React from "react";
import { ActivityIndicator, StyleSheet, View } from "react-native";
import { useRecoilState, useRecoilValue, useResetRecoilState } from "recoil";
import { useNavigation } from "@react-navigation/native";

import RaceCreateUnit from "./RaceCreateUnit";
import { raceCreateInfoState } from "../../../state/race/RaceState";
import { COLOR } from "../../../utils/constants";
import { loadingState } from "../../../state/loading/LoadingState";
import LoadingIndicator from "../../../utils/LoadingIndicator";
import RaceCreateView from "./RaceCreateView";
import { navigateWithHistory } from "../../../utils/util";
import { memberTokenState } from "../../../state/member/MemberState";
import { RaceApi } from "../../../utils/api/RaceApi";

const InputRaceInfo = () => {
  // eslint-disable-next-line prettier/prettier
  const {title, description, start_date, end_date, category, entrance_fee, days, start_time, end_time} = useRecoilValue(raceCreateInfoState);
  const resetRaceCreateInfo = useResetRecoilState(raceCreateInfoState);
  const [loading, setGlobalLoading] = useRecoilState(loadingState);
  const token = useRecoilValue(memberTokenState);
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
      certification_available_duration:{
        start_time,
        end_time,
      },
    };
  };

  const createRaceRequest = async () => {
    setGlobalLoading(true);
    try {
      const location = await RaceApi.post(token, formatPostRaceBody());

      resetRaceCreateInfo();
      navigateWithHistory(navigation, [
        { name: "Home" },
        {
          name: "RaceDetail",
          params: { location },
        },
      ]);
    } catch (e) {
      alert(e.toString());
    }
    setGlobalLoading(false);
  };

  const submitRaceRequest = async () => {
    if (!entrance_fee) {
      alert("필드를 모두 채워주세요");
      return;
    }
    if (entrance_fee < 0) {
      alert("입장료는 음수가 될 수 없습니다.");
      return;
    }

    try {
      await createRaceRequest();
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <LoadingIndicator>
      <RaceCreateView onPress={submitRaceRequest}>
        <RaceCreateUnit fieldName="entrance_fee" number>
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

export default InputRaceInfo;
