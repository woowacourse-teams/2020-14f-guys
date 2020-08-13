import React, { useEffect } from "react";
import { StyleSheet, Text, View } from "react-native";
import RaceItems from "./RaceItems";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { userTokenState } from "../../atoms";
import { loadingState } from "../../../state/loading/LoadingState";
import Axios from "axios";
import { SAMPLE_IMAGES, SERVER_BASE_URL } from "../../../utils/constants";
import { raceResponseState } from "../../../state/race/ResponseState";

const RaceList = () => {
  const setRaces = useSetRecoilState(raceResponseState);
  const setIsLoading = useSetRecoilState(loadingState);
  const token = useRecoilValue(userTokenState);
  const myRaces = useRecoilValue(raceResponseState);

  useEffect(() => {
    const fetchRaces = async () => {
      setIsLoading(true);
      const response = await Axios({
        method: "GET",
        baseURL: SERVER_BASE_URL,
        url: "/api/queries/races",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      const races =
        response.data.race_responses.length === 0
          ? []
          : response.data.race_responses.forEach((raceResponse) =>
              races.push(raceResponse)
            );
      setRaces(races);
    };
    fetchRaces();
    setIsLoading(false);
  }, []);

  return myRaces.length !== 0 ? (
    <View style={styles.container}>
      <Text style={styles.title}>달리고 있는 레이스들</Text>
      <View style={styles.raceItems}>
        <RaceItems races={myRaces} hasRace={true} />
      </View>
    </View>
  ) : (
    <View style={styles.container}>
      <Text style={styles.title}>달릴 수 있는 레이스들</Text>
      <View style={styles.raceItems}>
        <RaceItems races={SAMPLE_IMAGES} hasRace={false} />
      </View>
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
});

export default RaceList;
