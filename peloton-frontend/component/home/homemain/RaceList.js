import React, { useEffect } from "react";
import { StyleSheet, Text, View } from "react-native";
import RaceItems from "./RaceItems";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import { loadingState } from "../../../state/loading/LoadingState";
import { raceResponseState } from "../../../state/race/ResponseState";
import { memberTokenState } from "../../../state/member/MemberState";
import { QueryApi } from "../../../utils/api/QueryApi";
import { COLOR } from "../../../utils/constants";

const RaceList = () => {
  const [races, setRaces] = useRecoilState(raceResponseState);
  const setIsLoading = useSetRecoilState(loadingState);
  const token = useRecoilValue(memberTokenState);

  useEffect(() => {
    setIsLoading(true);
    const fetchRaces = async () => {
      try {
        const { race_responses } = await QueryApi.getRaces(token);
        setRaces(race_responses);
      } catch (error) {
        alert(error.response.data.code);
        console.log(error.response.data.message);
        setRaces([]);
      }
    };
    fetchRaces();
    setIsLoading(false);
  }, []);

  return (
    <View style={styles.container}>
      <Text style={styles.title}>
        {races.length !== 0
          ? "달리고 있는 레이스들"
          : "달리고 있는 레이스가 없네요 😭"}
      </Text>
      {races.length !== 0 ? (
        <View style={styles.raceItems}>
          <RaceItems />
        </View>
      ) : (
        <Text style={styles.empty}>텅</Text>
      )}
    </View>
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
  empty: {
    fontSize: 380,
    fontWeight: "600",
    marginTop: 30,
    color: COLOR.GRAY1,
    textAlign: "center",
  },
});

export default RaceList;
