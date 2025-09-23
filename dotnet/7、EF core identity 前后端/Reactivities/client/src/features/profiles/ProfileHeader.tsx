import { Avatar, Box, Chip, Grid2, Paper, Stack, Typography } from "@mui/material";
import { useParams } from "react-router";
import { useProfile } from "../../lib/hooks/useProfile";

export default function ProfileHeader() {
    const { id } = useParams();
    const { profile, isCurrentUser } = useProfile(id);

    if (!profile) return null;

    return (
        <Paper elevation={3} sx={{ p: 4, borderRadius: 3 }}>
            <Grid2 container spacing={2}>
                <Grid2 size={8}>
                    <Stack direction='row' spacing={3} alignItems='center'>
                        <Avatar
                            src={profile.imageUrl}
                            alt={profile.displayName + ' image'}
                            sx={{ width: 150, height: 150 }}
                        />
                        <Box display='flex' flexDirection='column' gap={2}>
                            <Typography variant="h4">{profile.displayName}</Typography>
                            {profile.following && <Chip
                                variant="outlined"
                                color="secondary"
                                label='Following'
                                sx={{ borderRadius: 1 }}
                            />}
                        </Box>
                    </Stack>
                </Grid2>
                <Grid2 size={3}>
                    <Stack spacing={2} alignItems='center'>
                        <Box display='flex' justifyContent='space-around' width='100%'>
                            <Box textAlign='center'>
                                <Typography variant="h6">Followers</Typography>
                                <Typography variant="h3">{profile.followersCount}</Typography>
                            </Box>
                            <Box textAlign='center'>
                                <Typography variant="h6">Following</Typography>
                                <Typography variant="h3">{profile.followingCount}</Typography>
                            </Box>
                        </Box>
                    </Stack>
                </Grid2>
                <Grid2 size={1}>
                    {isCurrentUser ? (
                        <Box textAlign='center'>
                            <Typography variant="h6">isCurrentUser</Typography>
                            <Typography variant="h3">true</Typography>
                        </Box>
                    ) : (
                        <Box textAlign='center'>
                            <Typography variant="h6">isCurrentUser</Typography>
                            <Typography variant="h3">false</Typography>
                        </Box>
                    )}
                </Grid2>
            </Grid2>
        </Paper>
    )
}