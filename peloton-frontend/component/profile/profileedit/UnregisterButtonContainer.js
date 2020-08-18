import React, { useEffect } from "react";
import { Alert, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { useNavigation } from "@react-navigation/native";
import Axios from "axios";
import { useRecoilValue } from "recoil";
import {
  COLOR,
  SERVER_BASE_URL,
  TOKEN_STORAGE,
} from "../../../utils/constants";
import {
  memberInfoState,
  memberTokenState,
} from "../../../state/member/MemberState";
import { MemberApi } from "../../../utils/api/MemberApi";
import { navigateWithoutHistory } from "../../../utils/util";
import AsyncStorage from "@react-native-community/async-storage";
import { useResetRecoilState, useSetRecoilState } from "recoil";
import { loadingState } from "../../../state/loading/LoadingState";
import { raceInfoState } from "../../../state/race/RaceState";
import { raceResponseState } from "../../../state/race/ResponseState";
import { ridersInfoState } from "../../../state/rider/RiderState";

const UnregisterButtonContainer = () => {
  const navigation = useNavigation();
  const token = useRecoilValue(memberTokenState);
  const setIsLoading = useSetRecoilState(loadingState);
  const resetMemberTokenState = useResetRecoilState(memberTokenState);
  const resetMemberInfoState = useResetRecoilState(memberInfoState);
  const resetRaceCreateInfoState = useResetRecoilState(memberTokenState);
  const resetRaceInfoState = useResetRecoilState(raceInfoState);
  const resetRaceResponseState = useResetRecoilState(raceResponseState);
  const resetRidersInfoState = useResetRecoilState(ridersInfoState);

  const requestUnregister = async () => {
    setIsLoading(true);
    await MemberApi.delete(token);
    await AsyncStorage.removeItem(TOKEN_STORAGE);
    navigateWithoutHistory(navigation, "Login");
    setIsLoading(false);
    resetMemberTokenState();
    resetMemberInfoState();
    resetRaceCreateInfoState();
    resetRaceInfoState();
    resetRaceResponseState();
    resetRidersInfoState();
  };

  const createTwoButtonAlert = async () =>
    await Alert.alert(
      "Unregister",
      "정말로 탈퇴하시겠습니까?",
      [
        {
          text: "Nope",
          onPress: () =>
            console.log("Someone tried to unregister. But didn't."),
          style: "cancel",
        },
        { text: "Yes", onPress: requestUnregister },
      ],
      { cancelable: false },
    );

  return (
    <View style={styles.unregisterBtnContainer}>
      <TouchableOpacity style={styles.button} onPress={createTwoButtonAlert}>
        <Text style={styles.buttonText}>Unregister</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  unregisterBtnContainer: {
    flex: 1,
    alignItems: "center",
    paddingBottom: 50,
  },
  button: {
    marginTop: 50,
    borderWidth: 1,
    width: 150,
    height: 50,
    borderColor: COLOR.WHITE3,
    borderRadius: 100,
    alignItems: "center",
    justifyContent: "center",
  },
  buttonText: {
    color: COLOR.GREEN2,
    fontSize: 14,
  },
});

export default UnregisterButtonContainer;
