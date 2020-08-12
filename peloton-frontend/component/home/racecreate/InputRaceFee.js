import React from "react";
import { ActivityIndicator, StyleSheet, View } from "react-native";
import { useRecoilState, useRecoilValue, useResetRecoilState } from "recoil";
import { useNavigation } from "@react-navigation/native";
import Axios from "axios";

import RaceCreateUnit from "./RaceCreateUnit";
import { raceCreateInfoState } from "../../../state/race/RaceState";
import { COLOR, SERVER_BASE_URL } from "../../../utils/constants";
import { loadingState } from "../../../state/loading/LoadingState";
import LoadingIndicator from "../../../utils/LoadingIndicator";
import RaceCreateView from "./RaceCreateView";
import { navigateWithHistory } from "../../../utils/util";
import { memberTokenState } from "../../../state/member/MemberState";

const InputRaceInfo = () => {
  // eslint-disable-next-line prettier/prettier
  const {title, description, start_date, end_date, category, entrance_fee, days, certification_available_duration} = useRecoilValue(raceCreateInfoState);
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
      days: ["MONDAY", "TUESDAY", "FRIDAY"],
      certification_available_duration: {
        start_time: "08:00:00",
        end_time: "10:00:00",
      },
    };
  };

  const createRaceRequest = async () => {
    setGlobalLoading(true);
    try {
      const response = await Axios({
        method: "post",
        baseURL: SERVER_BASE_URL,
        url: "/api/races",
        data: formatPostRaceBody(),
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      resetRaceCreateInfo();
      navigateWithHistory(navigation, [
        { name: "Home" },
        {
          name: "RaceDetail",
          params: { location: response.headers.location },
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
