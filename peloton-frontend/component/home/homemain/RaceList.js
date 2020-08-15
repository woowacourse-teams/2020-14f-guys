import React, { useEffect } from "react";
import { StyleSheet, Text, View } from "react-native";
import RaceItems from "./RaceItems";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { loadingState } from "../../../state/loading/LoadingState";
import { raceResponseState } from "../../../state/race/ResponseState";
import { memberTokenState } from "../../../state/member/MemberState";
import { QueryApi } from "../../../utils/api/QueryApi";
import LoadingIndicator from "../../../utils/LoadingIndicator";

const RaceList = () => {
  const setRaces = useSetRecoilState(raceResponseState);
  const setIsLoading = useSetRecoilState(loadingState);
  const token = useRecoilValue(memberTokenState);
  const myRaces = useRecoilValue(raceResponseState);

  useEffect(() => {
    const fetchRaces = async () => {
      setIsLoading(true);
      const response = await QueryApi.getRaces(token);

      setRaces(response.race_responses);
    };
    fetchRaces();
    setIsLoading(false);
  }, []);

  return myRaces.length !== 0 ? (
    <LoadingIndicator>
      <View style={styles.container}>
        <Text style={styles.title}>달리고 있는 레이스들</Text>
        <View style={styles.raceItems}>
          <RaceItems />
        </View>
      </View>
    </LoadingIndicator>
  ) : (
    <LoadingIndicator>
      <View style={styles.container}>
        <Text style={styles.title}>달릴 수 있는 레이스들</Text>
        <View style={styles.raceItems}>
          <RaceItems />
        </View>
      </View>
    </LoadingIndicator>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 50,
  },
  raceItems: {
    marginTop: 17,
  },
  title: {
    paddingLeft: 35,
    fontSize: 18,
    fontWeight: "600",
  },
});

export default RaceList;
