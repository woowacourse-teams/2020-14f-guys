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
import { useRecoilValue } from "recoil";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";
import { useNavigation, CommonActions } from "@react-navigation/native";
import useAxios from "axios-hooks";

import RaceCreateUnit from "./RaceCreateUnit";
import { raceCreateInfoState } from "../../../state/race/CreateState";
import { BASE_URL, COLOR } from "../../../utils/constants";

const InputRaceInfo = () => {
  // eslint-disable-next-line prettier/prettier
  const { title, description, startDate, endDate, category, entranceFee } = useRecoilValue(raceCreateInfoState);

  const formatPostRaceBody = () => {
    // eslint-disable-next-line prettier/prettier
    return { title, description, category, entranceFee, raceDuration: { startDate, endDate } };
  };

  const [{ response, loading, error }, createRaceRequest] = useAxios(
    {
      method: "post",
      baseURL: BASE_URL,
      url: "/api/races",
      data: formatPostRaceBody(),
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
  }, [response, error]);

  const onPress = async () => {
    // eslint-disable-next-line prettier/prettier
    if (!title || !description || !category || !entranceFee || !startDate || !endDate) {
      alert("필드를 모두 채워주세요");
      return;
    }
    if (startDate > endDate) {
      alert("레이스 종료 날짜가 시작 날짜보다 빠릅니다.");
      return;
    }
    if (entranceFee < 0) {
      alert("입장료는 음수가 될 수 없습니다.");
      return;
    }

    createRaceRequest();
  };

  return (
    <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
      <KeyboardAwareScrollView extraHeight={150} style={styles.container}>
        <RaceCreateUnit fieldName="title">제목</RaceCreateUnit>
        <RaceCreateUnit fieldName="description">설명</RaceCreateUnit>
        <RaceCreateUnit date fieldName="startDate">
          시작 날짜
        </RaceCreateUnit>
        <RaceCreateUnit date fieldName="endDate">
          종료 날짜
        </RaceCreateUnit>
        <RaceCreateUnit fieldName="entranceFee" number>
          입장료
        </RaceCreateUnit>
        <TouchableOpacity style={styles.button} onPress={onPress}>
          <Text style={styles.buttonText}>만들기</Text>
        </TouchableOpacity>
        {loading && (
          <View style={styles.loadingIndicator}>
            <ActivityIndicator size="large" color={COLOR.GRAY5} />
          </View>
        )}
      </KeyboardAwareScrollView>
    </TouchableWithoutFeedback>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 30,
    paddingBottom: 20,
    paddingHorizontal: 35,
  },
  button: {
    width: 178,
    height: 50,
    borderRadius: 100,
    backgroundColor: "#ffffff",
    shadowColor: "rgba(0, 0, 0, 0.3)",
    shadowOffset: {
      width: 0,
      height: 20,
    },
    shadowRadius: 50,
    shadowOpacity: 1,
    marginBottom: 50,
    alignSelf: "center",
    justifyContent: "center",
    alignItems: "center",
  },
  buttonText: {
    fontSize: 14,
  },
  loadingIndicator: {
    position: "absolute",
    top: 0,
    bottom: 0,
    left: 0,
    right: 0,
    backgroundColor: COLOR.GRAY1,
    opacity: 0.8,
    justifyContent: "center",
    alignItems: "center",
  },
});

export default InputRaceInfo;
