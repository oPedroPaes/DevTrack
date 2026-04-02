import { useEffect, useState } from "react";

import Navbar from "../components/Navbar";
import { getUserData, type User } from "../services/user";
import { getDashboard, type DashboardData } from "../services/dashboard";
import Card from "../components/Card";

function Dashboard() {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [user, setUser] = useState<User | null>(null);
  const [dashboard, setDashboard] = useState<DashboardData | null>(null);

  useEffect(() => {
    async function load() {
      try {
        const userData = await getUserData();
        setUser(userData);

        const dashboardData = await getDashboard(userData.id);
        setDashboard(dashboardData);
      } catch (err) {
        console.error(err);
        setError("Erro ao carregar dados");
      } finally {
        setLoading(false);
      }
    }

    load();
  }, []);

  if (loading) return <p>Carregando...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div>
      <Navbar />
      <div style={{ padding: "20px" }}>
        <h1>Dashboard</h1>
        <p>Bem-vindo, {user?.name}</p>

        <div style={gridStyle}>
          <Card title="Hoje" value={`${dashboard?.todayMinutes} min`} />
          <Card title="Semana" value={`${dashboard?.weekMinutes} min`} />
          <Card title="Mês" value={`${dashboard?.monthMinutes} min`} />
          <Card
            title="Média Semanal"
            value={`${dashboard?.weeklyAverage} min`}
          />
          <Card
            title="Média Mensal"
            value={`${dashboard?.monthlyAverage} min`}
          />
        </div>
      </div>
    </div>
  );
}

const gridStyle: React.CSSProperties = {
  display: "grid",
  gridTemplateColumns: "repeat(auto-fit, minmax(150px, 1fr))",
  gap: "20px",
  marginTop: "20px",
};

export default Dashboard;
