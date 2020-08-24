import React, { useEffect } from "react";
import { Alert, ScrollView, StyleSheet } from "react-native";
import { QueryApi } from "../../../utils/api/QueryApi";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil/dist";
import {
  memberInfoState,
  memberTokenState,
} from "../../../state/member/MemberState";
import { achievementRatesState } from "../../../state/certification/AchievementRatesState";
import { loadingState } from "../../../state/loading/LoadingState";
import AchievementItem from "./AchievementItem";

const AchievementItems = () => {
  const token = useRecoilValue(memberTokenState);
  const member = useRecoilValue(memberInfoState);
  const [achievementRates, setAchievementRates] = useRecoilState(
    achievementRatesState
  );
  const setIsLoading = useSetRecoilState(loadingState);

  useEffect(() => {
    setIsLoading(true);
    const fetchRaceAchievementRate = async () => {
      try {
        const { race_responses: races } = await QueryApi.getRaces(token);
        const response = await Promise.all(
          races.map(async (race) => {
            const {
              race_id,
              race_title,
              race_achievement_rates: list,
            } = await QueryApi.getRaceAchievement(token, race.id);
            const [{ achievement, certification_count }] = list.filter(
              (item) => item.member_id === member.id
            );
            return { race_id, race_title, achievement, certification_count };
          })
        );
        setAchievementRates(response);
      } catch (e) {
        Alert.alert("", e.response.data.code);
        console.log(e.response.data.message);
      }
      setIsLoading(false);
    };
    fetchRaceAchievementRate();
  }, []);

  return (
    <ScrollView horizontal={true} contentContainerStyle={styles.container}>
      {achievementRates.map((achievementRate) => (
        <AchievementItem
          key={achievementRate.race_id}
          achievement={achievementRate.achievement}
          raceTitle={achievementRate.race_title.substr(0, 8)}
          certificationCount={achievementRate.certification_count}
        />
      ))}
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: "row",
    marginHorizontal: 20,
    marginVertical: 10,
  },
});

export default AchievementItems;
