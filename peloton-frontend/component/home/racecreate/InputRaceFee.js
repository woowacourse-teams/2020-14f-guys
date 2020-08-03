import React, { useEffect } from "react";
import {
  ActivityIndicator,
  Keyboard,
  StyleSheet,
  Text,
  TouchableOpacity,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";
import { CommonActions, useNavigation } from "@react-navigation/native";
import useAxios from "axios-hooks";

import RaceCreateUnit from "./RaceCreateUnit";
import { raceCreateInfoState } from "../../../state/race/CreateState";
import { COLOR, SERVER_BASE_URL } from "../../../utils/constants";
import { loadingState } from "../../../state/loading/LoadingState";
import LoadingIndicator from "../../../utils/LoadingIndicator";
import { userTokenState } from "../../atoms";
import RaceCreateView from "./RaceCreateView";

const InputRaceInfo = () => {
  // eslint-disable-next-line prettier/prettier
  const { title, description, startDate, endDate, category, entranceFee } = useRecoilValue(raceCreateInfoState);
  const setGlobalLoading = useSetRecoilState(loadingState);
  const token = useRecoilValue(userTokenState);

  const formatPostRaceBody = () => {
    // eslint-disable-next-line prettier/prettier
    return { title, description, category, entranceFee, raceDuration: { startDate, endDate } };
  };

  const [{ response, loading, error }, createRaceRequest] = useAxios(
    {
      method: "post",
      baseURL: SERVER_BASE_URL,
      url: "/api/races",
      data: formatPostRaceBody(),
      headers: {
        Authorization: `Bearer ${token}`,
      },
    },
    { manual: true }
  );
  const navigation = useNavigation();

  useEffect(() => {
    if (response) {
      navigation.dispatch({
        ...CommonActions.reset({
          index: 1,
          routes: [
            {
              name: "HomeMain",
            },
            {
              name: "RaceDetail",
              params: { location: response.headers.location },
            },
          ],
        }),
        target: navigation.dangerouslyGetState().key,
      });
    }
    if (error) {
      alert(error.toString());
    }
    setGlobalLoading(loading);
  }, [response, loading, error]);

  const onPress = async () => {
    if (!entranceFee) {
      alert("필드를 모두 채워주세요");
      return;
    }
    if (entranceFee < 0) {
      alert("입장료는 음수가 될 수 없습니다.");
      return;
    }

    createRaceRequest()
      .then(() => navigation.navigate("RaceDetail"))
      .catch((err) => console.log(err));
  };

  return (
    <LoadingIndicator>
      <RaceCreateView onPress={onPress}>
        <RaceCreateUnit fieldName="entranceFee" number>
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
  subject: {
    fontSize: 35,
    fontWeight: "bold",
    lineHeight: 35,
    letterSpacing: 0,
    textAlign: "left",
    color: "#1b1c20",
  },
});

export default InputRaceInfo;
