import { SearchOff } from "@mui/icons-material";
import { Paper, Typography } from "@mui/material";
export default function NotFound() {
  return (
    <Paper
        sx={{
            height: 400,
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center',
            alignItems: 'center',
            p: 6
        }}
    >
        <SearchOff sx={{fontSize: 100}} color="primary" />
        <Typography gutterBottom variant="h3">
            Oops - we could not find what you are looking for
        </Typography>
    </Paper>
  )
}