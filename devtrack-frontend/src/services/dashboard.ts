import { apiFetch } from "./api";

export type DashboardData = {
  todayMinutes: number;
  weekMinutes: number;
  monthMinutes: number;
  weeklyAverage: number;
  monthlyAverage: number;
};

export async function getDashboard(userId: string): Promise<DashboardData> {
  const response = await apiFetch(`dashboard/${userId}`);

  if (!response.ok) {
    throw new Error("Erro ao buscar dashboard");
  }

  const data = await response.json();
  return data as DashboardData;
}
