import React from "react";
import { StyleSheet, Text, TouchableOpacity, View } from "react-native";
import AsyncStorage from "@react-native-community/async-storage";
import { useNavigation } from "@react-navigation/core";
import { navigateWithoutHistory } from "../../../utils/util";
import { COLOR, TOKEN_STORAGE } from "../../../utils/constants";
import { useResetRecoilState, useSetRecoilState } from "recoil";
import { loadingState } from "../../../state/loading/LoadingState";
import {
  memberInfoState,
  memberTokenState,
} from "../../../state/member/MemberState";
import { raceInfoState } from "../../../state/race/RaceState";
import { raceResponseState } from "../../../state/race/ResponseState";
import { ridersInfoState } from "../../../state/rider/RiderState";

const SignOutButtonContainer = () => {
  const navigation = useNavigation();
  const setIsLoading = useSetRecoilState(loadingState);
  const resetMemberTokenState = useResetRecoilState(memberTokenState);
  const resetMemberInfoState = useResetRecoilState(memberInfoState);
  const resetRaceCreateInfoState = useResetRecoilState(memberTokenState);
  const resetRaceInfoState = useResetRecoilState(raceInfoState);
  const resetRaceResponseState = useResetRecoilState(raceResponseState);
  const resetRidersInfoState = useResetRecoilState(ridersInfoState);

  const onSignOut = async () => {
    setIsLoading(true);
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

  return (
    <View style={styles.signOutBtnContainer}>
      <TouchableOpacity style={styles.button} onPress={onSignOut}>
        <Text style={styles.buttonText}>Sign Out</Text>
      </TouchableOpacity>
      <Text style={styles.version}>Version 4.8.42</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  signOutBtnContainer: {
    flex: 1,
    alignItems: "center",
    paddingBottom: 600,
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
    color: COLOR.GREEN3,
    fontSize: 14,
  },
  version: {
    paddingTop: 15,
    color: COLOR.GREEN3,
    fontSize: 12,
  },
});

export default SignOutButtonContainer;
