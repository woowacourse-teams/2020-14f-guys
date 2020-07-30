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
    <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
      <LoadingIndicator>
        <KeyboardAwareScrollView extraHeight={150} style={styles.container}>
          <View style={styles.subjectContainer}>
            <Text style={styles.subject}>{"Create" + "\n" + "Your Race"}</Text>
          </View>
          <RaceCreateUnit fieldName="entranceFee" number>
            Race의 입장료를 결정해주세요
          </RaceCreateUnit>
          <TouchableOpacity style={styles.button} onPress={onPress}>
            <Text style={styles.buttonText}>NEXT</Text>
          </TouchableOpacity>
          {loading && (
            <View style={styles.loadingIndicator}>
              <ActivityIndicator size="large" color={COLOR.GRAY5} />
            </View>
          )}
        </KeyboardAwareScrollView>
      </LoadingIndicator>
    </TouchableWithoutFeedback>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#FFFFFF",
    paddingTop: 67,
    paddingBottom: 20,
    paddingHorizontal: 33,
  },
  subjectContainer: {
    marginBottom: 120,
  },
  subject: {
    fontSize: 30,
    fontWeight: "bold",
    lineHeight: 35,
    letterSpacing: 0,
    textAlign: "left",
    color: "#1b1c20",
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
    marginTop: 100,
    alignSelf: "center",
    justifyContent: "center",
    alignItems: "center",
  },
  buttonText: {
    fontSize: 14,
  },
});

export default InputRaceInfo;
